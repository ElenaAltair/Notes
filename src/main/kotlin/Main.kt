/*
    title string // Заголовок заметки. Обязательный параметр
    text string // Текст заметки. Обязательный параметр
    privacy integer // Уровень доступа к заметке. Возможные значения:
                    • 0 — все пользователи,
                    • 1 — только друзья,
                    • 2 — друзья и друзья друзей,
                    • 3 — только пользователь.
    comment_privacy integer //Уровень доступа к комментированию заметки. Возможные значения:
                        • 0 — все пользователи,
                        • 1 — только друзья,
                        • 2 — друзья и друзья друзей,
                        • 3 — только пользователь.
privacy_view string // Настройки приватности просмотра заметки в специальном формате.
privacy_comment string //Настройки приватности комментирования заметки в специальном формате.


Приватность возвращается в API в виде массива из следующих возможных значений:
        • all – Доступно всем пользователям;
        • friends – Доступно друзьям текущего пользователя;
        • friends_of_friends / friends_of_friends_only – Доступно друзьям и друзьям друзей / друзьям друзей текущего пользователя (friends_of_friends_only появился с версии 5.32);
        • nobody / only_me – Недоступно никому / доступно только мне;
        • list{list_id} – Доступно друзьям текущего пользователя из списка с идентификатором {list_id};
        • {user_id} – Доступно другу с идентификатором {user_id};
        • -list{list_id} – Недоступно друзьям текущего пользователя из списка с идентификатором {list_id};
        • -{user_id} – Недоступно другу с идентификатором {user_id}.

   */

data class Note(
    // Заметка
    var id: Int,
    var title: String,
    var text: String,
    var privacy: Int = 0,
    var comment_privacy: Int = 0,
    //var privacy_view: Privacy?,
    //var privacy_comment: Privacy?,
    var listComments: ListComments = ListComments()
)

class ListNotes {
    private val notes = mutableListOf<Note>()
    fun addNote(note: Note): Note {
        notes.add(note)
        return notes.last()
    }

    fun deleteNote(id: Int): Int {
        val index = getindexOf(id)
        if (index != null) {
            notes.removeAt(index)
            return 1
        }
        return 0
    }

    fun editNote(id: Int, noteEdit: Note): Int {
        val index = getindexOf(id)
        if (index != null) {
            notes[index] = noteEdit
            return 1
        }
        return 0
    }

    fun getNote(): MutableList<Note> { // Возвращает список заметок, созданных пользователем.
        return notes
    }

    fun getById(id: Int): Note? { // Возвращает заметку по её id.
        for (note in notes) {
            if (note.id == id) {
                return note
            }
        }
        return null
    }

    private fun getindexOf(id: Int): Int? { // Возвращает индекс элемента в списке
        for (note in notes) {
            if (note.id == id) {
                return notes.indexOf(note)
            }
        }
        return null
    }

    fun createComment(id: Int, comment: Comment): MutableList<Comment>? {
        val index = getindexOf(id)
        if (index != null) {
            return notes[index].listComments.addComment(comment)
        }
        return null
    }

    fun deleteComment(id: Int, idComment: Int): Int {
        val index = getindexOf(id)
        if (index != null) {
            return notes[index].listComments.deleteComment(idComment)
        }
        return 0
    }

    fun editComment(id: Int, idComment: Int, сommentEdit: Comment): Int {
        val index = getindexOf(id)
        if (index != null) {
            return notes[index].listComments.editComment(idComment, сommentEdit)
        }
        return 0
    }

    fun getComments(id: Int): MutableList<Comment>? { // Возвращает список комментариев, созданных пользователем.
        val index = getindexOf(id)
        if (index != null) {
            return notes[index].listComments.getComments()
        }
        return null
    }

    fun restoreComment(id: Int, idComment: Int): Int { // Восстанавливает удалённый комментарий
        val index = getindexOf(id)
        if (index != null) {
            notes[index].listComments.getById(idComment)?.delete = false
            return 1
        }
        return 0
    }

}

/*
data class Privacy(
    // Приватность
    var all: Boolean, // Доступно всем пользователям;
    var friends: Boolean?, // Доступно друзьям текущего пользователя
    var friends_of_friends: Boolean?, // Доступно друзьям и друзьям друзей
    var friends_of_friends_only: Boolean?, // друзьям друзей текущего пользователя
    var nobody: Boolean?, // Недоступно никому
    var only_me: Boolean?, // доступно только мне
    var listFriends: ObjArrayFriends?, // Доступно друзьям текущего пользователя из списка с идентификатором {list_id}
    var idFriend: Int?, // Доступно другу с идентификатором {user_id}
    var listFriendsNo: ObjArrayFriends?, // Недоступно друзьям текущего пользователя из списка с идентификатором {list_id}
    var idFriendNo: Int?, // Недоступно другу с идентификатором {user_id}
)

data class User(
    var id: Long
)

class ArrayFriends { // массив со списком идентификаторов друзей
    private var friends = emptyArray<Long>()
    fun addIdFriend(friend: User): Array<Long> {
        friends += friend.id
        return friends
    }
}

data class ObjArrayFriends( //объект со списком друзей
    var id: Long,
    var friends: ArrayFriends
)
*/
data class Comment(
    var id: Int,
    var message: String, //Текст комментария
    var delete: Boolean = false // false - не удалён, true - удалён
)

class ListComments{
    private val сomments = mutableListOf<Comment>()
    fun addComment(сomment: Comment): MutableList<Comment> {
        сomments.add(сomment)
        return сomments
    }

    fun deleteComment(id: Int): Int {
        val index = getindexOf(id)
        if (index != null) {
            сomments[index].delete = true
            return 1
        }
        return 0

    }

    fun editComment(id: Int, сommentEdit: Comment): Int {
        val index = getindexOf(id)
        if (index != null && !сomments[index].delete) {
            сomments[index] = сommentEdit
            return 1
        }
        return 0
    }

    fun getComments(): MutableList<Comment>? { // Возвращает список неудалённых комментариев
        val сommentsVisible = mutableListOf<Comment>()
        val сommentsVisibleIterator = сomments.iterator()
        while (сommentsVisibleIterator.hasNext()) {
            val comment = сommentsVisibleIterator.next()
            if (!comment.delete) {
                сommentsVisible.add(comment)
            }

        }
        if (сommentsVisible.isEmpty()) {
            return null
        }
        return сommentsVisible


    }

    fun getById(id: Int): Comment? { // Возвращает комментарий по её id.
        for (сomment in сomments) {
            if (сomment.id == id) {
                return сomment
            }
        }
        return null
    }

    private fun getindexOf(id: Int): Int? { // Возвращает индекс элемента в списке
        for (сomment in сomments) {
            if (сomment.id == id) {
                return сomments.indexOf(сomment)
            }
        }
        return null
    }

}

fun main() {
    //val privacy = Privacy(true, null, null, null, null, null, null, null, null, null)
    val note1 = Note(1, "Заметка 1", "Текст заметки 1", 0, 0)
    val note2 = Note(2, "Заметка 2", "Текст заметки 2", 0, 0)
    val note3 = Note(3, "Заметка 3", "Текст заметки 3", 0, 0)
    val listNotes = ListNotes()
    listNotes.addNote(note1)
    listNotes.addNote(note2)
    listNotes.addNote(note3)
    println(listNotes.getNote())
    println(listNotes.getById(2))
    listNotes.deleteNote(1)

    println(listNotes.getNote())
    listNotes.editNote(2, note1)
    println(listNotes.getNote())

    val comment1 = Comment(1, "Комментарий 1", false)
    val comment2 = Comment(2, "Комментарий 2", false)
    val comment3 = Comment(3, "Комментарий 3", false)
    val comment4 = Comment(4, "Комментарий 4", false)
    listNotes.createComment(1, comment1)
    listNotes.createComment(1, comment2)
    listNotes.createComment(1, comment3)
    println(listNotes.getNote())
    println(listNotes.getComments(1))
    listNotes.deleteComment(1, 2)
    println(listNotes.getComments(1))
    listNotes.restoreComment(1, 2)
    println(listNotes.getComments(1))
    listNotes.editComment(1, 1, comment4)
    println(listNotes.getComments(1))
    listNotes.deleteComment(1, 2)
    println(listNotes.getComments(1))
    listNotes.editComment(1, 3, comment1)
    println(listNotes.getComments(1))
}