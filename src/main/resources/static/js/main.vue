var messageApi = Vue.resource('/message{/id}');

Vue.component('message-form', {
    template: <div><input type="text" placeholder="Write something"></div>
}

Vue.component('message-row', {
    props: ['message'],
    template: '<div>{{ message.id }}, {{ message.text }}</div>'
})

Vue.component('messages-list', {
    props: ['messages'],
    created: function(){
        messageApi.get().then (result =>
        result.json().then(data =>
        data.forEach(message => this.messages.push(message))
    )
)
    },
    template:   '<div>' +
                    '<div v-for="message in messages">' +
                        '<message-row :message="message"/>' +
                    '</div>' +
                '</div>'
})

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: [

        ]
    }
})