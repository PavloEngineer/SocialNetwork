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
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Faniyuki.com%2Fru%2Fgrustnye-anime-avatarki%2F&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABAL",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Faniyuki.com%2Fru%2Favatarki-zxc-70-foto%2F&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABAU",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Ftgstat.ru%2Fen%2Fchannel%2F%40kawaiianimeavatarochki&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABAd",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fklike.net%2F1345-kartinki-na-avatarku-50-foto.html&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABAm",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fotkrit-ka.ru%2F14051-prikolnye-multjashnye-avatarki.html&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABAw",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.volynpost.com%2Fnews%2F30370-nova-fishka-socmerezh-patriotychni-avatarky-foto&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABA5",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fdzen.ru%2Fa%2FYLMimTIE7WECrGu4&psig=AOvVaw0EZlQmSCnDHtnj6iznOoNv&ust=1692086760820000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi6lqTY24ADFQAAAAAdAAAAABBC"
        )
    }

}