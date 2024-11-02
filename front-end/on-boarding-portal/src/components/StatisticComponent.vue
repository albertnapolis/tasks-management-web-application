<template>
    <div class="statistic-container mt-4">
        <div class="mb-3">
            <h2>Statistics</h2>
        </div>
        <div class="container p-0 mb-3">
            <div class="row m-0">
                <div class="mandatory-card col-lg col-sm-12 card me-1 mb-1">
                    <div class="card-body">
                      <h3 class="card-title">Mandatory Tasks</h3>
                      <h5 class="card-text">{{taskStore.doneMandatoryTaskCount}}/{{ taskStore.mandatoryTaskCount }} tasks</h5>
                    </div>
                </div>
    
                <div class="todo-card col-lg col-sm-12 card me-1 mb-1">
                    <div class="card-body">
                      <h3 class="card-title">Todo Tasks</h3>
                      <h5 class="card-text">{{taskStore.tasksTodoCount}}/{{taskStore.allTaskCount}} tasks</h5>
                    </div>
                </div>
    
                <div class="inprogress-card col-lg col-sm-12 card me-1 mb-1">
                    <div class="card-body">
                      <h3 class="card-title">In Progress Tasks</h3>
                      <h5 class="card-text">{{taskStore.tasksInProgressCount}}/{{taskStore.allTaskCount}} tasks</h5>
                    </div>
                </div>
    
                <div class="done-card col-lg col-sm-12 card mb-1">
                    <div class="card-body">
                      <h3 class="card-title">Done Tasks</h3>
                      <h5 class="card-text">{{taskStore.tasksDoneCount}}/{{taskStore.allTaskCount}} tasks</h5>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        <div id="content">
            <div class="mb-5">
                <h3>Weekly Tasks Report</h3>
                <h5>Date: {{currentDate}}</h5>
            </div>
            <hr>
            <div class="mb-3">
                <div class="fs-5">Total Number of Tasks: {{taskStore.allTaskCount}}</div>
                <div class="fs-5">Total Number of Finished Tasks: {{taskStore.tasksDoneCount}}</div>
                <div class="fs-5">Total Number of In Progress Tasks: {{taskStore.tasksInProgressCount}}</div>
                <div class="fs-5">Total Number of Todo Tasks: {{taskStore.tasksTodoCount}}</div>
            </div>
            <div class="mb-4">
                <div class="fs-5">Total Number of Unfinished Mandatory Tasks: {{taskStore.mandatoryTaskCount - taskStore.doneMandatoryTaskCount}}</div>
            </div>
            <div class="mb-5">
                <div class="fs-4 mb-2">Summary:</div>
                <div>
                    <div>Task Completion Rate: Approximately <b>{{ (taskStore.tasksDoneCount / taskStore.allTaskCount*100).toFixed(1) }}%</b> of tasks are finished.</div>
                    <div>Tasks in Progress: <b>{{taskStore.tasksInProgressCount}}</b> tasks are currently being worked on.</div>
                    <div>Pending Tasks: <b>{{taskStore.tasksTodoCount}}</b> tasks are yet to be started.</div>
                    <div>Mandatory Tasks: A significant number <b>({{taskStore.mandatoryTaskCount - taskStore.doneMandatoryTaskCount}})</b> of mandatory tasks remain unfinished.</div>
                </div>
            </div>
        </div>
        <hr>
        <div class="d-flex mb-3">
            <button @click="printToPDF" class="btn btn-primary me-2">Download as PDF</button>
            <email-component/>
        </div>
    </div>
</template>

<script>
import { useTaskStore } from '@/stores/TaskStore';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import EmailComponent from './EmailComponent.vue';
    export default {
        components: {
            EmailComponent
        },
        setup() {
            const taskStore = useTaskStore()

            taskStore.resetStore()
            taskStore.getAllTask()

            const printToPDF = async () => {
                const content = document.getElementById('content');
                const canvas = await html2canvas(content);
                const imgData = canvas.toDataURL('image/png');
                const pdf = new jsPDF();
                
                pdf.addImage(imgData, 'PNG', 10, 10);
                pdf.save('document.pdf');
            }

            return {
                taskStore,
                username: localStorage.getItem('username'),
                currentDate: new Date().toISOString().split('T')[0],
                printToPDF
            }
        }
    }
</script>

<style scoped>
.mandatory-card {
    background: #FFBF44;
    color: white;
}
.todo-card {
    background: #8b5cf6;
    color: white;
}
.inprogress-card {
    background: #3B82F6;
    color: white;
}
.done-card {
    background: #10B981;
    color: white;
}
</style>