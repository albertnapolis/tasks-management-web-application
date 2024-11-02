<template>
    <div class="header-container">
        <div class="container p-0">
            <div class="d-flex align-items-center justify-content-between text-white p-2">
                <div>
                    <h4><router-link to="/">ONBOARDING</router-link></h4>
                </div>
                <div class="d-none d-lg-block">
                    <div class="d-flex">
                        <div class="p-3"><router-link to="/">Dashboard</router-link></div>
                            <div class="p-3"><router-link to="/tasks">Tasks</router-link></div>
                            <div class="p-3"><router-link to="/reports">Report</router-link></div>
                            <div class="p-3 text-white" @click="useTasks.logout()">
                            <span class="material-symbols-outlined" style="cursor: pointer">
                                logout
                            </span>
                        </div>
                    </div>
                </div>
                <div class="burger d-sm-block d-lg-none">
                    <button class="burger-btn" @click="showMenu()">
                        <div></div>
                        <div></div>
                        <div></div>
                    </button>
                </div>
            </div>
            <div class="menu-container text-center bg-dark d-lg-block" v-if="hideMenu">
                <div class="p-3"><router-link to="/">Dashboard</router-link></div>
                <div class="p-3"><router-link to="/tasks">Tasks</router-link></div>
                <div class="p-3"><router-link to="/reports">Report</router-link></div>
                <div class="p-3 text-white" @click="useTasks.logout()">Logout</div>
            </div>
        </div>
    </div>
</template>

<script>
import { useTaskStore } from '@/stores/TaskStore';
import { ref } from 'vue';
    export default {
        setup() {
            const useTasks = useTaskStore()
            const hideMenu = ref(false)

            const showMenu = () => {
                hideMenu.value = !hideMenu.value
            }

            return {
                showMenu,
                hideMenu,
                useTasks,
                username: localStorage.getItem('username')
            }
        }
    }
</script>

<style scoped>
.header-container{
    background: #0055CC;
}
.burger-btn {
    border: none;
    background: none;
}
.burger-btn div {
    width: 35px;
    height: 5px;
    background-color: white;
    margin: 6px 0;
}
.menu-container {
    position: absolute;
    right: 0;
    border-bottom-left-radius: 10px;
    box-shadow: -10px 5px 2px rgba(202, 202, 202, 0.2);
    z-index: 1;
    width: 250px;
}

.menu-container a {
    text-decoration: none;
    color: white;
}
.menu-container div {
    padding-left: 5px;
}
a {
    text-decoration: none;
    color: white;
}
</style>