<template>
    <div class="sign-up-container d-flex flex-column align-items-center">
        <div class="w-100">
            <div class="mb-3">
                <img src="../assets/undraw_Add_tasks_re_s5yj.png" alt="task-management" class="task-management-img">
            </div>
            <div class="w-100">
                <div v-if="error" :class="[{'error-message': !success, 'error-success': success }, 'mb-2']">
                    *{{ error }}
                </div>
                <form @submit.prevent="onSubmit" class="d-flex flex-column ">
                    <input type="text" v-model="form.username" placeholder="username" required autocomplete="username" class="mb-2 p-2">
                    <input type="email" v-model="form.email" placeholder="email" required autocomplete="email" class="mb-2 p-2">
                    <input :type="showPassword ? 'text' : 'password'" v-model="form.password" placeholder="password" required autocomplete="new-password" class="mb-2 p-2">
                    <input :type="showPassword ? 'text' : 'password'" v-model="confirmPassword" placeholder="confirm password" required autocomplete="new-password" class="mb-2 p-2">
                    <div class="mb-1">
                        <input type="checkbox" v-model="showPassword" id="showPassword" class="me-1">
                        <label for="showPassword">Show Password</label>
                    </div>
                    <button type="submit" class="signup-btn text-white border">Sign up</button>
                </form>
            </div>
            <div class="w-100 mt-2">
                <p>Already have an account? <router-link to="/sign-in">Sign in</router-link></p>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {reactive, ref} from 'vue'
    import { useReCaptcha } from 'vue-recaptcha-v3'
    const { executeRecaptcha, recaptchaLoaded } = useReCaptcha()
    const form = reactive({ 
        username: '', 
        email: '',
        password: ''
    })

    const error = ref('')
    const showPassword = ref(false)
    const confirmPassword = ref('')
    const success = ref(false)

    const onSubmit = async () => {
        await recaptchaLoaded()
        const token = await executeRecaptcha('signup')
        if (form.password !== confirmPassword.value) {
            error.value = 'Passwords do not match.';
        } else {
            error.value = ''

                error.value = ''
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/users/register`, {
                method: 'POST',
                headers: { 
                    'recaptcha': token,
                    'Content-Type': 'application/json' 
                },
                body: JSON.stringify(form)
            })
            const data = await response.json()
            if(!response.ok) {
                error.value = data.message
            } else {
                error.value = "Registered Successfully."
                success.value = true
                resetForm() 
            }
        }
    }

    const resetForm = () => {
        form.username = ''
        form.email = ''
        form.password = ''
        confirmPassword.value = ''
    }

</script>

<style scoped>
.sign-up-container {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 300px;
}
.task-management-img {
    width: 100%;
}
.signup-btn {
    background: #0055CC;
    border: none;
    height: 50px;
}
.error-message {
    color: red;
}
.error-success {
    color: green;
}
</style>