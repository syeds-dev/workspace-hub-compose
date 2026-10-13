package com.syedsubahani.workspacehub.ui.contactscreen

import com.syedsubahani.workspacehub.common.Status
import com.syedsubahani.workspacehub.data.repository.FakeContactsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ContactsViewModelTest {

    private lateinit var viewModel: ContactsViewModelTest
    private lateinit var fakeContactsRepositoryTest: FakeContactsRepositoryImpl

    @Before
    fun setup(){
        viewModel = ContactsViewModelTest()
    }

    @Test
    fun `set should return network error`(){
        runBlocking {
            fakeContactsRepositoryTest = FakeContactsRepositoryImpl()
            var value = fakeContactsRepositoryTest.setShouldReturnNetworkError()
            Assert.assertEquals(value, "Network Error")
        }
    }

    @Test
    fun `calling getContacts for not null check`(){
        runBlocking {
            fakeContactsRepositoryTest = FakeContactsRepositoryImpl()
            var contactList = fakeContactsRepositoryTest.getContacts(Status.SUCCESS)
            Assert.assertNotNull(contactList)

        }
    }

    @Test
    fun `calling getContacts with success parameter`(){
        runBlocking {
            fakeContactsRepositoryTest = FakeContactsRepositoryImpl()
            var contactList = fakeContactsRepositoryTest.getContacts(Status.SUCCESS)
            Assert.assertTrue("Contact List has two contacts", contactList.data?.size == 2)

        }
    }
    @Test
    fun `calling getContacts with error parameter`(){
        runBlocking {
            fakeContactsRepositoryTest = FakeContactsRepositoryImpl()
            var contactList = fakeContactsRepositoryTest.getContacts(Status.ERROR)
            Assert.assertTrue("Error Occurred", contactList.message.equals("An Error occurred"))
        }
    }
}