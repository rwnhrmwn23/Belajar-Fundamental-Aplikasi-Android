package com.onedev.consumerapp.helper

import android.database.Cursor
import com.onedev.consumerapp.model.ItemUser
import com.onedev.consumerapp.helper.SupportHelper.USER_AVATAR_URL
import com.onedev.consumerapp.helper.SupportHelper.USER_ID
import com.onedev.consumerapp.helper.SupportHelper.USER_USERNAME

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<ItemUser> {
        val itemUserList = ArrayList<ItemUser>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(USER_ID))
                val avatarUrl = getString(getColumnIndexOrThrow(USER_AVATAR_URL))
                val username = getString(getColumnIndexOrThrow(USER_USERNAME))
                itemUserList.add(ItemUser(id, avatarUrl, username))
            }
        }
        return itemUserList
    }

    fun mapCursorToObject(notesCursor: Cursor?): ItemUser {
        var itemUser = ItemUser()
        notesCursor?.let {
            if (notesCursor.moveToFirst()) {
                notesCursor.apply {
                    val id = getInt(getColumnIndexOrThrow(USER_ID))
                    val avatarUrl = getString(getColumnIndexOrThrow(USER_AVATAR_URL))
                    val username = getString(getColumnIndexOrThrow(USER_USERNAME))
                    itemUser = ItemUser(id, avatarUrl, username)
                }
            } else {
                itemUser = ItemUser()
            }
            notesCursor.close()
        }
        return itemUser
    }
}