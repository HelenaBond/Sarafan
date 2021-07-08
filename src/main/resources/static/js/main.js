function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var messageApi = Vue.resource('/message{/id}'); //url по которому мы обращаемся к серверу
// Определяем компоненты до создания Vue
 Vue.component('message-row', { // делаем каждую строку из data отдельным компонетом
     props: ['message', 'editMethod', 'messages'],
     template: '<div>' +
         '<i>({{ message.id }})</i> {{ message.text }}' +
         '<span style="position: absolute; right: 0">' +
            '<input type="button" value="Edit" @click="edit" />' +
            '<input type="button" value="x" @click="del" />' +
         '</span>' +
         '</div>',
     methods: {
         edit: function () { // передаем сообщение в форму редактирования
             this.editMethod(this.message);
         },
         del: function () {
             messageApi.remove({id: this.message.id}).then(result =>{
                 if (result.ok) { //http status 200
                     this.messages.splice(this.messages.indexOf(this.message), 1)
                 }
             })
         }
     }
 });

 // форма
 Vue.component('message-form', {
     props: ['messages', 'messageAttr'],
     // во всех компонентах данные (data) не объект - а функция, которая возвращает объект.
     data: function () {
         return {
             text: '', // данные только что введённого сообщения в форму
             id: ''
         }
     },
     watch: {
         messageAttr: function (newVal) {
             this.text = newVal.text;
             this.id = newVal.id;
         }
     },
     template: '<div>' + // в rest вместо <form> можно писать <div> в темплейте
         '<input type="text" placeholder="Write something" v-model="text" />' +
         '<input type="button" value="Save" @click="save" />' + // @ - обработка DOM-событий
         '</div>',
     methods: {
         save: function () { // реализация того КАК будет сохранятся сообщение
             var message = {text: this.text};
             if (this.id) {
                 messageApi.update({id: this.id}, message).then(result => // берем id из пути
                    result.json().then(data => {
                        var  index = getIndex(this.messages, data.id)
                        this.messages.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                 )
             } else {
                 messageApi.save({}, message).then(result => // сохраняем сообщение в базу
                     result.json().then(data => { // и тут же возвращаем это сообщение вместе с сгенерированным id.
                         this.messages.push(data);
                         this.text = ''
                     })
                 )
             }
         }
     }
 });

Vue.component('messages-list', {
    props: ['messages'], // данные (data) из app
    data: function () { // объект для хранения текущего сообщения из "message-row"
        return {
            message: null
        }
    },
    template: '<div style="position: relative; width: 300px;">' +
        // отображение формы на экране
        '<message-form :messages="messages" :messageAttr="message" />' + // привязываем текущее сообщение к props формы
        '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
        ':editMethod="editMethod" :messages="messages"/>' +
        '</div>', //циклом выводим список из props построково, цикл работает только внутри контейнера
    created: function () { //все верхнеуровневые функции (в каждом компоненте тоже) не стрелочные, а анонимные
        messageApi.get().then(result => // получаем поток данных из базы
            result.json().then(data => //получаем данные из json()
                data.forEach(message => this.messages.push(message)) //помещаем данные в props
            )
        )
    },
    methods: {
        editMethod: function (message) { // текущее сообщение помещаем в data компонента
            this.message = message;
        }
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