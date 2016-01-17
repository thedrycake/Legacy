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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Common entity utility methods.
 */
public abstract class EntityUtils {

	private static final String[] PROJECTION_ID = new String[] { BaseColumns._ID };

	public static ContentValues getEntity(Context context, Uri uri,
			String[] projection) {
		return getEntity(context.getContentResolver(), uri, projection);
	}

	public static ContentValues getEntity(ContentResolver cr, Uri uri,
			String[] projection) {
		return getEntity(cr, uri, projection, null, null);
	}

	public static ContentValues getEntity(Context context, Uri uri,
			String[] projection, String selection, String[] selectionArgs) {
		return getEntity(context.getContentResolver(), uri, projection,
				selection, selectionArgs);
	}

	public static ContentValues getEntity(ContentResolver cr, Uri uri,
			String[] projection, String selection, String[] selectionArgs) {
		return getEntity(cr, uri, projection, selection, selectionArgs, null);
	}

	public static ContentValues getEntity(Context context, Uri uri,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		return getEntity(context.getContentResolver(), uri, projection,
				selection, selectionArgs, sortOrder);
	}

	public static ContentValues getEntity(ContentResolver cr, Uri uri,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		ContentValues values = null;
		Cursor cursor = null;
		try {
			cursor = cr.query(uri, projection, selection, selectionArgs,
					sortOrder);
			if (cursor.moveToNext()) {
				values = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cursor, values);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return values;
	}

	public static long getEntityId(Context context, Uri uri) {
		return getEntityId(context.getContentResolver(), uri);
	}

	public static long getEntityId(ContentResolver cr, Uri uri) {
		return getEntityId(cr, uri, null, null);
	}

	public static long getEntityId(Context context, Uri uri, String selection,
			String[] selectionArgs) {
		return getEntityId(context.getContentResolver(), uri, selection,
				selectionArgs);
	}

	public static long getEntityId(ContentResolver cr, Uri uri,
			String selection, String[] selectionArgs) {
		return getEntityId(cr, uri, selection, selectionArgs, null);
	}

	public static long getEntityId(Context context, Uri uri, String selection,
			String[] selectionArgs, String sortOrder) {
		return getEntityId(context.getContentResolver(), uri, selection,
				selectionArgs, sortOrder);
	}

	public static long getEntityId(ContentResolver cr, Uri uri,
			String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		try {
			cursor = cr.query(uri, PROJECTION_ID, selection, selectionArgs,
					sortOrder);
			if (cursor.moveToNext()) {
				return cursor.getLong(0);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return -1;
	}

	public static List<ContentValues> getEntityList(Context context, Uri uri,
			String[] projection) {
		return getEntityList(context.getContentResolver(), uri, projection);
	}

	public static List<ContentValues> getEntityList(ContentResolver cr,
			Uri uri, String[] projection) {
		return getEntityList(cr, uri, projection, null, null);
	}

	public static List<ContentValues> getEntityList(Context context, Uri uri,
			String[] projection, String selection, String[] selectionArgs) {
		return getEntityList(context.getContentResolver(), uri, projection,
				selection, selectionArgs);
	}

	public static List<ContentValues> getEntityList(ContentResolver cr,
			Uri uri, String[] projection, String selection,
			String[] selectionArgs) {
		return getEntityList(cr, uri, projection, selection, selectionArgs,
				null);
	}

	public static List<ContentValues> getEntityList(Context context, Uri uri,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		return getEntityList(context.getContentResolver(), uri, projection,
				selection, selectionArgs, sortOrder);
	}

	public static List<ContentValues> getEntityList(ContentResolver cr,
			Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		List<ContentValues> result = new ArrayList<ContentValues>();
		Cursor cursor = null;
		try {
			cursor = cr.query(uri, projection, selection, selectionArgs,
					sortOrder);
			while (cursor.moveToNext()) {
				ContentValues values = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cursor, values);
				result.add(values);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return result;
	}

	public static List<Long> getEntityIdList(Context context, Uri uri) {
		return getEntityIdList(context.getContentResolver(), uri);
	}

	public static List<Long> getEntityIdList(ContentResolver cr, Uri uri) {
		return getEntityIdList(cr, uri, null, null);
	}

	public static List<Long> getEntityIdList(Context context, Uri uri,
			String selection, String[] selectionArgs) {
		return getEntityIdList(context.getContentResolver(), uri, selection,
				selectionArgs);
	}

	public static List<Long> getEntityIdList(ContentResolver cr, Uri uri,
			String selection, String[] selectionArgs) {
		return getEntityIdList(cr, uri, selection, selectionArgs, null);
	}

	public static List<Long> getEntityIdList(Context context, Uri uri,
			String selection, String[] selectionArgs, String sortOrder) {
		return getEntityIdList(context.getContentResolver(), uri, selection,
				selectionArgs, sortOrder);
	}

	public static List<Long> getEntityIdList(ContentResolver cr, Uri uri,
			String selection, String[] selectionArgs, String sortOrder) {
		List<Long> result = new ArrayList<Long>();
		Cursor cursor = null;
		try {
			cursor = cr.query(uri, PROJECTION_ID, selection, selectionArgs,
					sortOrder);
			while (cursor.moveToNext()) {
				result.add(cursor.getLong(0));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return result;
	}

	public static Set<Long> getEntityIdSet(Context context, Uri uri) {
		return getEntityIdSet(context.getContentResolver(), uri);
	}

	public static Set<Long> getEntityIdSet(ContentResolver cr, Uri uri) {
		return getEntityIdSet(cr, uri, null, null);
	}

	public static Set<Long> getEntityIdSet(Context context, Uri uri,
			String selection, String[] selectionArgs) {
		return getEntityIdSet(context.getContentResolver(), uri, selection,
				selectionArgs);
	}

	public static Set<Long> getEntityIdSet(ContentResolver cr, Uri uri,
			String selection, String[] selectionArgs) {
		Set<Long> result = new HashSet<Long>();
		Cursor cursor = null;
		try {
			cursor = cr.query(uri, PROJECTION_ID, selection, selectionArgs,
					null);
			while (cursor.moveToNext()) {
				result.add(cursor.getLong(0));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return result;
	}

}
