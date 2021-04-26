import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


var articlesLastId = 0
val articles = mutableListOf<Article>()

fun addArticles(title: String, body: String): Int {
    val id = articlesLastId + 1

    val reg = LocalDateTime.now()
    val up = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val forReg = reg.format((formatter))
    val forUp = up.format((formatter))
    val article = Article(id, title, body, forReg, forUp)
    articles.add(article)
    articlesLastId = id
    return id
}

fun makeTestArticles() {
    for (id in 1..1000) {
        val title = "제목$id"
        val body = "내용$id"

        addArticles(title, body)
    }


}

fun main() {
    println("== 게시판 관리 프로그램 시작 ==")

    // 가장 마지막에 입력된 게시물 번호
    makeTestArticles()


    loop@ while (true) {

        print("명령어) ")
        val command = readLine()!!.split(" ").map{it.toString()}
        val str =command[0]+" "+command[1]
        if((str).startsWith("article exit")){
                println("프로그램을 종료합니다.")
                break@loop
            }
        if(str.startsWith("article write"))memberWrite()


        if(str.startsWith("article list")) memberList(command)

        if(str.startsWith("article delete")){

                if (articles.any { it.id == command[2].toInt() }) {
                    println("${command[2]} was deleted")
                    articles.removeAt(command[2].toInt() - 1)
                } else {
                    println("${command[2]} is not exist")
                }
            }
        if(str.startsWith("article modify"))memberModify(command[2].toInt())




        }


    println("== 게시판 관리 프로그램 끝 ==")
}



data class Article(
    val id: Int,
    val title: String,
    val body: String,
    val regDate: String,
    val updateDate: String
)
fun memberDetail(num:Int){
    println("번호 : ${articles[num.toInt() - 1].id}")
    println("작성 날짜 : ${articles[num.toInt() - 1].regDate}")
    println("갱신 날짜 : ${articles[num.toInt() - 1].updateDate}")
    println("제목 : ${articles[num.toInt() - 1].title}")
    println("내용 : ${articles[num.toInt() - 1].body}")
}
fun memberModify(num:Int){
    print("새제목 : ")
    val title = readLine()!!.trim()
    print("새내용 : ")
    val body = readLine()!!.trim()
    val num = articles[num.toInt() - 1].id
    val currentDateTime = Calendar.getInstance().time
    val update = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDateTime)
    val reg = articles[num.toInt() - 1].regDate
    val temp = Article(num, title, body, reg, update)
    articles.removeAt(num.toInt() - 1)
    articles.add(num.toInt() - 1, temp)
}
fun memberList(info: List<String>){
    val temp = articles.reversed()
    if(info.size==3) {

        for (i in ((info[2].toInt() - 1) * 10) until (info[2].toInt() * 10)) {
            println("${temp[i].id} / ${temp[i].title} / ${temp[i].body} / ${temp[i].regDate} / ${temp[i].updateDate}")
        }
    }else if(info.size==4){

        var arr2 = mutableListOf<Article>()
        for (i in 0 until 1000){
            if(temp[i].title.contains(info[2]))
                arr2.add(temp[i])
        }
        for (i in ((info[3].toInt() - 1) * 5) until (info[3].toInt() * 5)) {
            println("${arr2[i].id} / ${arr2[i].title} / ${arr2[i].body} / ${arr2[i].regDate} / ${arr2[i].updateDate}")
        }

    }
}
fun memberWrite(){
    print("제목 : ")
    val title = readLine()!!.trim()
    print("내용 : ")
    val body = readLine()!!.trim()
    val id = addArticles(title, body)

    println("${id}번 게시물이 작성되었습니다.")
    articlesLastId = id
}
