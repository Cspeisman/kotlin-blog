package blog

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/articles")
class HttpApi(val repository: ArticleRepository, val markdownConverter: MarkdownConverter) {

  @GetMapping
  fun index() = repository.findAll();

  @GetMapping("/{id}")
  fun show(@PathVariable id: Long, @RequestParam converter: String?) = when (converter){
    "markdown" -> repository.findById(id).map { it.copy(
      headline = markdownConverter.invoke(it.headline),
      content = markdownConverter.invoke(it.content)
    )}
     null -> repository.findById(id)
    else -> throw IllegalArgumentException("Only markdown converter is acceped")
  }
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

  @GetMapping("/")
  fun findAll() = repository.findAll()

  @GetMapping("/{login}")
  fun findOne(@PathVariable login: String) = repository.findById(login)
}