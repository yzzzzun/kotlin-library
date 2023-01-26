package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작")
    fun saveBookTest() {

        val bookRequest = BookRequest("kotlin", BookType.COMPUTER)

        bookService.saveBook(bookRequest)

        val books = bookRepository.findAll()

        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("kotlin")
    }

    @Test
    @DisplayName("책 대출 정상 동작")
    fun loanBookTest() {
        bookRepository.save(Book.fixture("kotlin"))
        val savedUser = userRepository.save(User("test", null))

        val bookLoanRequest = BookLoanRequest("test", "kotlin")

        bookService.loanBook(bookLoanRequest)

        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("kotlin")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    @DisplayName("책 대출 실패 - 이미 대출 된 경우")
    fun loanBookFailTest() {
        bookRepository.save(Book.fixture("kotlin"))
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "kotlin"))
        val bookLoanRequest = BookLoanRequest("test", "kotlin")

        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(bookLoanRequest)
        }.message
        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반납 정상동작")
    fun refundBookTest() {
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "kotlin"))
        val bookReturnRequest = BookReturnRequest("test", "kotlin")

        bookService.returnBook(bookReturnRequest)

        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }
}
