<template>
    <div class="mb-5">
        <div class="d-flex flex-column align-items-center justify-content-center">
            <div class="text-center">
                <img src="../assets/welcome.jpg" class="img-fluid w-50"  alt="" srcset="">
            </div>
            <div class="text-center">
                <h1>Hello! {{ username }} </h1>
                <h1 class="mb-4">Welcome to the Team!</h1>
                <p class="lead">We're excited to have you onboard. Below are some important links to get you started</p>
                <p class="lead">We also added some important tasks in the tasks page. If you have any questions, don't hesitate to reach out.</p>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            username: localStorage.getItem('username')
        }
    }
}
</script>

<style scoped>

</style>
  