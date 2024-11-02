<template>
    <div class="container-fluid p-0 d-flex flex-column min-vh-100">
        <div>
            <nav-menu/>
        </div>
        <div class="content">
            <div class="container p-0">
                <div v-if="isHome">
                    <welcome-component/>
                    <hr>
                    <important-services/>
                </div>
                <div v-else-if="isTask">
                    <task-component/>
                </div>
                <div v-else-if="isReport">
                    <statistic-component/>
                </div>
            </div>
        </div>
        <div>
            <footer-component/>
        </div>
    </div>
</template>

<script>
import NavMenu from './NavMenu.vue'
import WelcomeComponent from './WelcomeComponent.vue'
import ImportantServices from './ImportantServices.vue'
import TaskComponent from './TaskComponent.vue'
import StatisticComponent from './StatisticComponent.vue'
import footerComponent from './footerComponent.vue'
export default {
    components: {
        NavMenu,
        WelcomeComponent,
        ImportantServices,
        TaskComponent,
        StatisticComponent,
        footerComponent
    },
    computed: {
        isHome() {
            return this.$route.path === '/'
        },

        isTask() {
            return this.$route.path === '/tasks'
        },

        isReport() {
            return this.$route.path === '/reports'
        }
    }
}
</script>

<style scoped>
.content {
    flex: 1;
}
</style>