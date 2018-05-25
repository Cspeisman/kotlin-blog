package blog

import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Arrays.asList
import java.util.Collections.singletonList

@ExtendWith(SpringExtension::class)
@WebMvcTest
class HttpApiTests(@Autowired val mockMvc: MockMvc) {

  @MockBean
  lateinit var articleRepository: ArticleRepository

  @MockBean
  lateinit var userRepository: UserRepository

  @MockBean
  lateinit var converter: MarkdownConverter

  @Test
  fun `List articles`() {
    val user = User("cspeisman", "Corey", "Speisman")
    val article = Article("Title 1", "Headline 1", "Headline 1", user, 1)
    whenever(articleRepository.findAll()).thenReturn(singletonList(article))
    mockMvc.perform(get("/api/articles/").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.size()").value(1))
  }

  @Test
  fun `List users`() {
    val user1 = User("cspeisman", "Corey", "Speisman")
    val user2 = User("aramos", "Alisha", "Ramos")
    whenever(userRepository.findAll()).thenReturn(asList(user1, user2))
    mockMvc.perform(get("/api/users/").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.size()").value(2))
      .andExpect(jsonPath("[0].firstname").value("Corey"))
  }
}