/* 
 * Copyright (C) 2013 The Drycake Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thedrycake.tempincity.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import android.util.Log;

/**
 * Common IO utility methods.
 */
public abstract class IoUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * Closes the object and release any system resources it holds.
	 * 
	 * @param closeable
	 *            the object to be closed.
	 * @throws IOException
	 *             if a I/O-related error occurs during the closing operation.
	 */
	public static void close(Closeable closeable) throws IOException {
		if (closeable != null) {
			closeable.close();
		}
	}

	/**
	 * Closes the object and release any system resources it holds, without
	 * throwing an exception if something goes wrong.
	 * 
	 * @param closeable
	 *            the object to be closed.
	 */
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			Log.w(IoUtils.class.getSimpleName(), "Error closing closeable: "
					+ closeable, e);
		}
	}

	/**
	 * Flushes the object by writing out any buffered data to the underlying
	 * output.
	 * 
	 * @param flushable
	 *            the object to be flushed.
	 * @throws IOException
	 *             if a I/O-related error occurs during the flushing operation.
	 */
	public static void flush(Flushable flushable) throws IOException {
		if (flushable != null) {
			flushable.flush();
		}
	}

	/**
	 * Flushes the object by writing out any buffered data to the underlying
	 * output, without throwing an exception if something goes wrong.
	 * 
	 * @param flushable
	 *            the object to be flushed.
	 */
	public static void flushQuietly(Flushable flushable) {
		try {
			if (flushable != null) {
				flushable.flush();
			}
		} catch (IOException e) {
			Log.w(IoUtils.class.getSimpleName(), "Error flushing flushable: "
					+ flushable, e);
		}
	}

	/**
	 * Copy bytes from an InputStream to an OutputStream.
	 * 
	 * @param input
	 *            the InputStream.
	 * @param output
	 *            the OutputStream.
	 * @return the number of bytes copied.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static long copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Copy bytes from an InputStream to characters on a Writer using the
	 * default charset.
	 * 
	 * @param input
	 *            the InputStream.
	 * @param output
	 *            the Writer.
	 * @return the number of bytes copied.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static long copy(InputStream input, Writer output)
			throws IOException {
		return copy(input, output, null);
	}

	/**
	 * Copy bytes from an InputStream to characters on a Writer using specified
	 * charset.
	 * 
	 * @param input
	 *            the InputStream.
	 * @param output
	 *            the Writer.
	 * @param charset
	 *            the charset to use, null means platform default.
	 * @return the number of bytes copied.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static long copy(InputStream input, Writer output, Charset charset)
			throws IOException {
		InputStreamReader in;
		if (charset != null) {
			in = new InputStreamReader(input, charset);
		} else {
			in = new InputStreamReader(input);
		}
		return copy(in, output);
	}

	/**
	 * Copy characters from a Reader to bytes on an OutputStream using the
	 * default charset.
	 * 
	 * @param input
	 *            the Reader.
	 * @param output
	 *            the OutputStream.
	 * @return the number of characters copied.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static long copy(Reader input, OutputStream output)
			throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(output);
		long count = copy(input, out);
		out.flush();
		return count;
	}

	/**
	 * Copy characters from a Reader to bytes on an OutputStream using the
	 * specified charset.
	 * 
	 * @param input
	 *            the Reader.
	 * @param output
	 *            the OutputStream.
	 * @param charset
	 *            the charset to use, null means platform default.
	 * @return the number of characters copied.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static long copy(Reader input, OutputStream output, Charset charset)
			throws IOException {
		long count;
		if (charset != null) {
			OutputStreamWriter out = new OutputStreamWriter(output, charset);
			count = copy(input, out);
			out.flush();
		} else {
			count = copy(input, output);
		}
		return count;
	}

	/**
	 * Copy characters from a Reader to a Writer.
	 * 
	 * @param input
	 *            the Reader.
	 * @param output
	 *            the Writer.
	 * @return the number of characters copied.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Get the contents of an InputStream as a byte[].
	 * 
	 * @param input
	 *            the InputStream.
	 * @return the requested byte[].
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * Get the contents of a Reader as a byte[] using the default charset.
	 * 
	 * @param input
	 *            the Reader.
	 * @return the requested byte[].
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static byte[] toByteArray(Reader input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * Get the contents of a Reader as a byte[] using the specified charset.
	 * 
	 * @param input
	 *            the Reader.
	 * @param charset
	 *            the charset to use, null means platform default.
	 * @return the requested byte[].
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static byte[] toByteArray(Reader input, Charset charset)
			throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output, charset);
		return output.toByteArray();
	}

	/**
	 * Get the contents of an InputStream as a String using the default charset.
	 * 
	 * @param input
	 *            the InputStream.
	 * @return the requested String.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	/**
	 * Get the contents of an InputStream as a String using the specified
	 * charset.
	 * 
	 * @param input
	 *            the InputStream.
	 * @param charset
	 *            the charset to use, null means platform default.
	 * @return the requested String.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static String toString(InputStream input, Charset charset)
			throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, charset);
		return sw.toString();
	}

	/**
	 * Get the contents of a byte[] as a String using the default charset.
	 * 
	 * @param input
	 *            the byte array.
	 * @return the requested String.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static String toString(byte[] input) throws IOException {
		return new String(input);
	}

	/**
	 * Get the contents of a byte[] as a String using the specified charset.
	 * 
	 * @param input
	 *            the byte array.
	 * @param charset
	 *            the charset to use, null means platform default.
	 * @return the requested String.
	 * @throws IOException
	 *             if a I/O-related error occurs.
	 */
	public static String toString(byte[] input, Charset charset)
			throws IOException {
		if (charset != null) {
			return new String(input, charset.name());
		} else {
			return new String(input);
		}
	}

}
