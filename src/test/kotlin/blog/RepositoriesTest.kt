package blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class RepositoriesTest(@Autowired val entityManager: TestEntityManager,
                       @Autowired val userRepository: UserRepository,
                       @Autowired val articleRepository: ArticleRepository) {
    @Test
    fun `When findById then return Article`() {
        val user = User("springjuergen", "Juergen", "Hoeller")
        entityManager.persist(user)
        val article = Article("Spring framework 5.0 goes GA", "Dear Spring community...", "Lorem ipson", user)
        entityManager.persist(article)
        entityManager.flush()

        val found = articleRepository.findById(article.id!!)
        assertThat(found.get()).isEqualTo(article)
    }

    @Test
    fun `When findById then return User`() {
        val juergen = User("Springjurgen", "Juergen", "Hoeller")
        entityManager.persist(juergen)
        entityManager.flush()

        val found = userRepository.findById(juergen.login)
        assertThat(found.get()).isEqualTo(juergen)
    }
}