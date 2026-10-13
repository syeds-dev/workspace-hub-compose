package com.syedsubahani.workspacehub.ui.roomscreen

import com.syedsubahani.workspacehub.common.Status
import com.syedsubahani.workspacehub.data.repository.FakeRoomsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RoomsViewModelTest {

    private lateinit var viewModel: RoomsViewModelTest
    private lateinit var fakeRoomsRepositoryTest: FakeRoomsRepositoryImpl

    @Before
    fun setup(){
        viewModel = RoomsViewModelTest()
    }

    @Test
    fun `set should return network error`(){
        runBlocking {
            fakeRoomsRepositoryTest = FakeRoomsRepositoryImpl()
            var value = fakeRoomsRepositoryTest.setShouldReturnNetworkError()
            Assert.assertEquals(value, "Network Error")
        }
    }

    @Test
    fun `calling getRooms for not null check`(){
        runBlocking {
            fakeRoomsRepositoryTest = FakeRoomsRepositoryImpl()
            var roomsList = fakeRoomsRepositoryTest.getRooms(Status.SUCCESS)
            Assert.assertNotNull(roomsList)

        }
    }

    @Test
    fun `calling getRooms with success parameter`(){
        runBlocking {
            fakeRoomsRepositoryTest = FakeRoomsRepositoryImpl()
            var roomsList = fakeRoomsRepositoryTest.getRooms(Status.SUCCESS)
            Assert.assertTrue("Rooms List has two rooms", roomsList.data?.size == 2)

        }
    }
    @Test
    fun `calling getRooms with error parameter`(){
        runBlocking {
            fakeRoomsRepositoryTest = FakeRoomsRepositoryImpl()
            var roomsList = fakeRoomsRepositoryTest.getRooms(Status.ERROR)
            Assert.assertTrue("Error Occurred", roomsList.message.equals("An Error occurred"))
        }
    }
}