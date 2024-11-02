<template>
    <div class="sign-in-container d-flex flex-column align-items-center">
        <div class="w-100">
            <div class="mb-3">
                <img src="../assets/undraw_Add_tasks_re_s5yj.png" alt="task-management" class="task-management-img">
            </div>
            <div class="w-1">
                <div v-if="error" class="error-message mb-2">
                    *{{ error }}
                </div>
                <form @submit.prevent="onSubmit" class="d-flex flex-column">
                    <input v-model="user.email" type="email" name="loginEmail" id="loginEmail" placeholder="email" required class="mb-2 p-2">
                    <input v-model="user.password" :type="showPassword ? 'text' : 'password'" name="loginPassword" id="loginPassword" placeholder="password" required class="mb-2 p-2">
                    <div class="mb-1">
                        <input type="checkbox" v-model="showPassword" id="showPassword" class="me-1">
                        <label for="showPassword">Show Password</label>
                    </div>
                    <button type="submit" class="g-recaptcha login-btn text-white">Login</button>
                </form>
                <div class="w-100 mt-2">
                    <p>Don't have an account? <router-link to="/sign-up">Sign up</router-link></p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from '@/router'
import { jwtDecode } from 'jwt-decode';
import {reactive, ref} from 'vue';
import { useReCaptcha } from 'vue-recaptcha-v3'

const user = reactive({ email: '', password: '' });
const { executeRecaptcha, recaptchaLoaded } = useReCaptcha()
const showPassword = ref(false);
const error = ref('');

const onSubmit = async () => {
    await recaptchaLoaded()
    const token = await executeRecaptcha('login')
    const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/auth`, {
        method: 'POST',
        headers: { 
            'recaptcha': token,
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(user)
    })

    const data = await response.json()
    if (!response.ok) {
        if(data.message === "Access Denied") {
            error.value = "Invalid email or password"
        } else {
            error.value = data.message
        }
    } else {
        localStorage.setItem('authToken', data.accessToken)
        const decodedToken = jwtDecode(data.accessToken)
        localStorage.setItem('userId', decodedToken.sub)
        localStorage.setItem('username', decodedToken.username)
        router.push("/") 
    } 
}
</script>

<style scoped>
    .login-btn {
        background: #0055CC;
        border: none;
        height: 50px;
    }
    .task-management-img {
        width: 100%;
    }
    .sign-in-container {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 300px;
    }
    .error-message {
        color: red;
    }
</style>