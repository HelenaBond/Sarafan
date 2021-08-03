<template>
    <div>
        <div v-if="!usName">Необходимо авторизоваться через
            <a href="/login">Google или Github</a>
        </div>
        <div v-else>
            <div>{{usName}}&nbsp<a href="/logout">Выйти</a></div>
            <messages-list :messages="messages"/>
        </div>
    </div>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'

    export default {
        components: {
            MessagesList
        },
        data() { //пишем ввиде функции, чтобы на каждый вызов создавался новый объект
            return {
                messages: frontendData.messages,
                usName: frontendData.usName
            }
        }
    }
</script>

<style>

</style>