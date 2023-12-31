package com.example.ebrain.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.ui.theme.NoteBlue
import com.example.ebrain.ui.theme.NoteGreen
import com.example.ebrain.ui.theme.NoteGrey
import com.example.ebrain.ui.theme.NoteGreyBlue
import com.example.ebrain.ui.theme.NoteGreyGreen
import com.example.ebrain.ui.theme.NoteOrange
import com.example.ebrain.ui.theme.NotePurple
import com.example.ebrain.ui.theme.NoteRed
import com.example.ebrain.ui.theme.NoteYellow

@Entity
data class Note(
    val title:String,
    val content:String,
    val timeStamp:Long,
    val privacy: Privacy,
    val color:Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val noteColor = listOf(NoteBlue, NoteGreen, NoteYellow, NoteOrange, NotePurple, NoteGrey, NoteRed, NoteGreyBlue, NoteGreyGreen)
    }
}
class InvalidNoteException(message: String): Exception(message)