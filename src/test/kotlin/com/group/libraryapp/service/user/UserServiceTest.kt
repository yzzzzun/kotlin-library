package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @AfterEach
    fun afterEach() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("사용자 추가 정상 동작")
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("test", null)

        //when
        userService.saveUser(request)

        //then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo(request.name)
        assertThat(results[0].age).isNull()
    }

    @Test
    @DisplayName("사용자 조회 정상 동작")
    fun getUserTest() {
        //given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )

        //when
        val users = userService.getUsers()

        //then
        assertThat(users).hasSize(2)
        assertThat(users).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(20, null)
    }

    @Test
    @DisplayName("사용자 수정 정상 동작")
    fun updateTest() {
        val savedUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(savedUser.id, "B")

        userService.updateUserName(request)

        val actual = userRepository.findAll()[0]
        assertThat(actual.name).isEqualTo("B")
    }

    @Test
    @DisplayName("사용자 삭제 정상 동작")
    fun deleteTest() {
        userRepository.save(User("A", null))

        userService.deleteUser("A")

        assertThat(userRepository.findAll()).isEmpty()
    }
}
