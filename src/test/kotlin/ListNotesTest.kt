import org.junit.Assert
import kotlin.test.Test


class ListNotesTest {

    @Test
    fun addNote() {
        val note1 = Note(1, "Заметка 1", "Текс заметки 1", 0, 0)
        val listNotes = ListNotes()

        Assert.assertEquals(note1, listNotes.addNote(note1))
    }

    @Test
    fun deleteNote1() { //удаление существующей заметки
        val note1 = Note(1, "Заметка 1", "Текс заметки 1", 0, 0, ListComments())
        val listNotes = ListNotes()
        listNotes.addNote(note1)

        Assert.assertEquals(1, listNotes.deleteNote(1))
    }
    @Test
    fun deleteNote2() { //удаление не существующей заметки
        val note1 = Note(1, "Заметка 1", "Текс заметки 1", 0, 0, ListComments())
        val listNotes = ListNotes()
        listNotes.addNote(note1)

        Assert.assertEquals(0, listNotes.deleteNote(2))
    }

    @Test
    fun createComment() {
        val note1 = Note(1, "Заметка 1", "Текс заметки 1", 0, 0, ListComments())
        val listNotes = ListNotes()
        listNotes.addNote(note1)
        val comment1 = Comment(1, "Комментарий 1", false)

        Assert.assertEquals(listNotes.createComment(1, comment1), listNotes.getComments(1))
    }

    @Test
    fun deleteComment1() { //если удаляем cуществующий комментарий
        val note1 = Note(1, "Заметка 1", "Текс заметки 1", 0, 0, ListComments())
        val listNotes = ListNotes()
        listNotes.addNote(note1)
        val comment1 = Comment(1, "Комментарий 1", false)
        listNotes.createComment(1, comment1)

        Assert.assertEquals(1, listNotes.deleteComment(1,1))
    }
    @Test
    fun deleteComment2() { //если удаляем комментарий у не существуещей заметки
        val note1 = Note(1, "Заметка 1", "Текст заметки 1", 0, 0, ListComments())
        val listNotes = ListNotes()
        listNotes.addNote(note1)

        Assert.assertEquals(0, listNotes.deleteComment(2,1))
    }
    @Test
    fun deleteComment3() { //если удаляем не сущетвующий комментарий у  существуещей заметки
        val note1 = Note(1, "Заметка 1", "Текст заметки 1", 0, 0, ListComments())
        val listNotes = ListNotes()
        listNotes.addNote(note1)

        Assert.assertEquals(0, listNotes.deleteComment(1,2))
    }
}