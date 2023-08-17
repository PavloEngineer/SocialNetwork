package com.shpp.application.level_2.model
import com.github.javafaker.Faker

typealias UserListeners = (users: List<User>) -> Unit
class UserService {

    private var users: MutableList<User> = mutableListOf<User>()

    private val listeners: MutableList<UserListeners> = mutableListOf()

    init {
        val faker: Faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..30).map { User (
            id = it.toInt(),
            name = faker.name().name(),
            job = faker.job().position(),
            address = faker.address().fullAddress(),
            photo = IMAGES[ it % IMAGES.size]
        ) } as MutableList<User>
    }

    fun getUsers(): List<User> {
        return users
    }

    fun deleteUser(user: User) {
        users = ArrayList(users)
        users.remove(user)
        notifyChanges()
    }

    fun addListener(listener: UserListeners) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun deleteListener(listener: UserListeners) {
        listeners.remove(listener)
    }

   private fun notifyChanges() {
        listeners.forEach{it.invoke(users)}
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://gravatar.com/avatar/ca09089ce4e4f4a5c1c408ab22d22a91?s=400&d=robohash&r=x",
            "https://gravatar.com/avatar/efd6858a5f746140ea07cec5733c7c74?s=400&d=robohash&r=x",
            "https://gravatar.com/avatar/8d615a2a1b27c4c3297fa5124305cbfc?s=400&d=robohash&r=x",
            "https://gravatar.com/avatar/a7bb3266897ad708becc0a5eaff0b557?s=400&d=robohash&r=x",
            "https://gravatar.com/avatar/e8f8c55bac6dc304540845c281f3783b?s=400&d=robohash&r=x",
            "https://gravatar.com/avatar/3a6bdb5d04f18e2fa215b69cc43a50c4?s=400&d=robohash&r=x",
            "https://gravatar.com/avatar/ba79001e355f4092cd4b47df9d1070a2?s=400&d=robohash&r=x"
        )
    }

}