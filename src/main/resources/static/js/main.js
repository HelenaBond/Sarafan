var messageApi = Vue.resource('/message{/id}'); //url по которому мы обращаемся к серверу

// Определяем компоненты до создания Vue
 Vue.component('message-row', { // делаем каждую строку из data отдельным компонетом
     props: ['message'],
     template: '<div><i>({{ message.id }})</i> {{ message.text }}</div>'
 });

Vue.component('messages-list', {
    props: ['messages'], // данные из data
    template: '<div>' +
        '<message-row v-for="message in messages" :key="message.id" :message="message"/>' +
        '</div>', //циклом выводим список из props построково, цикл работает только внутри контейнера
    created: function() { //все верхнеуровневые функции не стрелочные, а анонимные
        messageApi.get().then(result => // получаем поток данных из базы
            result.json().then(data => //получаем данные из json()
                data.forEach(message => this.messages.push(message)) //помещаем данные в props
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    //название компонента помещаем в тег и в ковычки
    template: '<messages-list :messages="messages"/>', // в свойствах указываем props
    data: { //эти данные можно обновлять только через методы массивов (push, splice, delete)
        messages: []
    }
});