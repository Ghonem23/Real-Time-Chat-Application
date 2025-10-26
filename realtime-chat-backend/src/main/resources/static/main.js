// === GLOBAL VARIABLES ===
let stompClient = null;
let currentUsername = null;

// === UTIL: Get current time ===
function getCurrentTime() {
    const now = new Date();
    return now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

// === CONNECT TO WEBSOCKET ===
function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({ username: currentUsername }, function () {
        console.log('Connected as', currentUsername);

        stompClient.subscribe('/topic/public', function (message) {
            const chatMessage = JSON.parse(message.body);
            if (chatMessage.sender !== currentUsername) {
                displayMessage(chatMessage);
            }
        });
    });
}

// === DISPLAY MESSAGE ===
function displayMessage(message) {
    const chatContainer = document.getElementById('chat-container');
    const msgWrapper = document.createElement('div');
    msgWrapper.classList.add('flex', 'flex-col', 'mb-2');

    const msgElement = document.createElement('div');
    msgElement.classList.add('p-3', 'rounded-lg', 'max-w-lg', 'break-words');

    const timestamp = document.createElement('div');
    timestamp.classList.add('text-gray-300', 'text-xs', 'mt-1', 'self-end');
    timestamp.textContent = getCurrentTime();

    if (message.type === 'JOIN' || message.type === 'LEAVE') {
        msgElement.classList.add('text-center', 'text-sm', 'font-medium');
        if (message.type === 'JOIN') {
            msgElement.classList.add('text-green-400');
            msgElement.textContent = message.content || `${message.sender} joined the chat.`;
        } else {
            msgElement.classList.add('text-red-400');
            msgElement.textContent = message.content || `${message.sender} left the chat.`;
        }
        msgWrapper.appendChild(msgElement);
        msgWrapper.appendChild(timestamp);
    } else {
        const senderPart = document.createElement('span');
        senderPart.classList.add('font-bold', 'text-indigo-400', 'mr-2');
        senderPart.textContent = message.sender + ':';
        const textPart = document.createElement('span');
        textPart.textContent = ' ' + message.content;

        msgElement.appendChild(senderPart);
        msgElement.appendChild(textPart);
        msgElement.appendChild(timestamp);

        if (message.sender === currentUsername) {
            msgElement.classList.add('bg-indigo-900', 'rounded-xl', 'ml-auto', 'p-2');
            msgWrapper.classList.add('items-end');
        } else {
            msgElement.classList.add('bg-gray-800', 'rounded-xl', 'p-2');
            msgWrapper.classList.add('items-start');
        }

        msgWrapper.appendChild(msgElement);
    }

    chatContainer.appendChild(msgWrapper);
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

// === SEND MESSAGE ===
function sendMessage(event) {
    event.preventDefault();
    const messageInput = document.getElementById('message-input');
    const messageContent = messageInput.value.trim();

    if (messageContent && stompClient && stompClient.connected) {
        const chatMessage = {
            sender: currentUsername,
            content: messageContent,
            type: 'CHAT'
        };
        displayMessage(chatMessage);
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}

// === HANDLE USERNAME ENTRY ===
document.addEventListener('DOMContentLoaded', function () {
    const overlay = document.getElementById('username-overlay');
    const startBtn = document.getElementById('start-chat');
    const usernameInput = document.getElementById('username-input');
    const chatSection = document.getElementById('chat-section');
    const changeBtn = document.getElementById('change-username');

    // ✅ Auto-login if username saved
    const savedName = localStorage.getItem('chatUsername');
    if (savedName) {
        currentUsername = savedName;
        overlay.style.display = 'none';
        chatSection.style.display = 'flex';
        connect();
    }

    // Handle joining
    startBtn.addEventListener('click', () => {
        const input = usernameInput.value.trim();
        if (input.length > 0) {
            currentUsername = input;
            localStorage.setItem('chatUsername', currentUsername);
            overlay.style.display = 'none';
            chatSection.style.display = 'flex';
            connect();
        } else {
            usernameInput.classList.add('ring-2', 'ring-red-500');
            setTimeout(() => usernameInput.classList.remove('ring-2', 'ring-red-500'), 1500);
        }
    });

    // ✅ Change username button
    changeBtn.addEventListener('click', () => {
        if (stompClient) stompClient.disconnect();
        localStorage.removeItem('chatUsername');
        currentUsername = null;

        chatSection.style.display = 'none';
        overlay.style.display = 'flex';
    });

    const sendBtn = document.getElementById('send-btn');
    const messageForm = document.getElementById('message-form');
    sendBtn.addEventListener('click', sendMessage);
    messageForm.addEventListener('submit', sendMessage);
});
