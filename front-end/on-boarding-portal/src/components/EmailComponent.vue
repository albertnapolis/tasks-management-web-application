<template>
    <div>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
            Send Mail
        </button>
        
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Send Report</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div v-if="error" class="container mb-2">
                            * {{ error }}
                        </div>
                        <form @submit.prevent="onSubmit" class="d-flex flex-column container">
                            <input v-model="form.toEmail" type="text" class="mb-2 p-3" placeholder="send to">
                            <input v-model="form.subject" type="text" class="mb-2 p-3" placeholder="subject">
                            <textarea v-model="form.body" class="mb-2 p-3" placeholder="email content"></textarea>
                            <button type="submit" @click="sendMail" class="btn btn-primary p-3">send</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="resetForm">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import { useReCaptcha } from 'vue-recaptcha-v3'
const { executeRecaptcha, recaptchaLoaded } = useReCaptcha()
const form = reactive({ 
    toEmail: '',
    subject: '',
    body: ''
})

const error = ref('')

const onSubmit = async () => {
    await recaptchaLoaded()
    const token = await executeRecaptcha('email')

    const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/email/sendmail`, {
        method: 'POST',
        headers: { 
            'recaptcha': token,
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(form)
    })

    if(!response.ok) {
        error.value = "Sending Failed"
    } else {
        error.value = "Email sent"
        resetForm()
    }
}

const resetForm = () => {
    form.toEmail = '',
    form.subject = '',
    form.body = ''
    
}

</script>

<style scoped>

</style>