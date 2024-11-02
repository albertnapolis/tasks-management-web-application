<template>
    <div class="container-fluid p-0">
        <div class="p-2 container">
            <div class="mb-3">
                <h2>Tasks</h2>
            </div>
            <hr>
            <div class="">
                <div class="d-flex align-items-center h-100 mb-3">
                    <button style="background: #10b981;" type="button" class="btn btn-primary me-2 d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                        <span class="material-symbols-outlined">
                            add
                        </span>
                    </button>
                    <div class="fs-4 fw-semibold">Create Task</div>
                </div>
                <div class="modal" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">Create Task</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <div data-mdb-input-init class="form-outline mb-4">
                                    <input v-model="task.taskName" type="text" id="form4Example1" class="form-control" placeholder="task name"/>
                                    </div>
                                
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <label class="form-label" for="form4Example3">Task Description</label>
                                        <textarea v-model="task.taskDescription" class="form-control" id="form4Example3" rows="4"></textarea>
                                    </div>
                                </form>
                                <div v-if="taskStore.error" class="mb-2">
                                    * {{ taskStore.error }}
                                </div>
                            </div>
                            <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" @click="createTask()">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container p-0">
                <div class="row m-0">
                    <div class="col-sm-12 col-lg me-4 p-0">
                        <div class="row m-0 todo-card p-3 mb-2 shadow">TODO</div>
                        <div class="row p-0 m-0" v-for="task in taskStore.tasksTodo" :key="task.userTasksId">
                            <div class="task-todo text-break p-0 mb-2">
                                <div class="shadow border p-2">
                                    <div style="color: #FFBF44" class="fs-6" v-if="task.task.isMandatory">Mandatory</div>
                                    <div style="color: #8b5cf6">
                                        <div class="fs-5">
                                            {{task.task.title}}
                                        </div>
                                    </div>
                                    <div class="fs-6 d-flex align-items-center pt-3" style="color: #8b5cf6">
                                        <span class="material-symbols-outlined me-1">
                                            event
                                        </span>
                                        {{ task.dateCreated }}
                                    </div>
                                    <hr>
                                    <button type="button" class="view-btn" style="color: #8b5cf6" @click="openModal(task)">
                                        View
                                    </button>
                                    |
                                    <button type="button" class="view-btn" style="color: #8b5cf6" @click="updateStatus(task)">
                                        Update Status
                                    </button>
                                    <div>
                                        <div class="modal fade" :id="'taskModal-' + task.userTasksId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                          <div class="modal-dialog">
                                            <div class="modal-content">
                                              <div class="modal-header d-flex flex-column align-items-start ">
                                                <label for="">Task Name: </label>
                                                <h5 class="modal-title w-100" id="exampleModalLabel">
                                                    <input type="text" v-model="task.task.title" class="p-2 w-100">
                                                </h5>
                                              </div>
                                              <div class="modal-body">
                                                <div class="w-100">
                                                    <label for="">Description: </label>
                                                    <textarea v-model="task.task.description" class="w-100 p-2" placeholder="description" style="resize: none" rows="4"></textarea>
                                                </div>
                                                <div>
                                                    <label>Status: </label> 
                                                    <div style="background: #8b5cf6;" class="w-25 text-white text-center p-2 rounded-1 mt-1">{{task.status}}</div>
                                                </div>
                                              </div>
                                              <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" @click="closeModal">Close</button>
                                                <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
                                                <button v-if="!task.task.isMandatory" type="button" class="btn btn-danger" @click="deleteTask(task)">Delete</button>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-lg me-4 p-0">
                        <div class="row m-0 inprogress-card p-3 mb-2 shadow">IN PROGRESS</div>
                        <div class="row p-0 m-0" v-for="task in taskStore.tasksInProgress" :key="task.userTasksId">
                            <div class="task-in-progress text-break p-0 mb-2">
                                <div class="shadow border p-2">
                                    <div style="color: #FFBF44" class="fs-6" v-if="task.task.isMandatory">Mandatory</div>
                                    <div class="" style="color: #3B82F6">
                                        <div class="fs-5">{{task.task.title}}</div>
                                    </div>
                                    <div class="fs-6 d-flex align-items-center pt-3" style="color: #3B82F6">
                                        <span class="material-symbols-outlined me-1 ">
                                            event
                                        </span>
                                        {{ task.dateCreated }}
                                    </div>
                                    <hr>
                                    <button type="button" class="view-btn" style="color: #3B82F6" @click="openModal(task)">
                                        View
                                    </button>
                                    |
                                    <button type="button" class="view-btn" style="color: #3B82F6" @click="updateStatus(task)">
                                        Update Status
                                    </button>
                                    <div>
                                        <div class="modal fade" :id="'taskModal-' + task.userTasksId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                          <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header d-flex flex-column align-items-start ">
                                                    <label for="">Task Name: </label>
                                                    <h5 class="modal-title w-100" id="exampleModalLabel">
                                                        <input type="text" v-model="task.task.title" class="p-2 w-100">
                                                    </h5>
                                                </div>
                                              <div class="modal-body">
                                                <div class="w-100">
                                                    <textarea v-model="task.task.description" class="w-100 p-2" placeholder="description" style="resize: none" rows="4"></textarea>
                                                </div>
                                                <div>
                                                    <label>Status: </label> 
                                                    <div style="background: #3B82F6;" class="w-25 text-white text-center p-2 rounded-1 mt-1">{{task.status}}</div>
                                                </div>
                                              </div>
                                              <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" @click="closeModal">Close</button>
                                                <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
                                                <button v-if="!task.task.isMandatory" type="button" class="btn btn-danger" @click="deleteTask(task)">Delete</button>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-lg p-0">
                        <div class="row m-0 done-card p-3 mb-2 shadow">DONE</div>
                        <div class="row p-0 m-0" v-for="task in taskStore.tasksDone" :key="task.userTasksId">
                            <div class="task-done text-break p-0 mb-2">
                                <div class="shadow border p-2">
                                    <div style="color: #FFBF44" class="fs-6" v-if="task.task.isMandatory">Mandatory</div>
                                    <div class="" style="color: #10B981">
                                        <div class="fs-5">{{task.task.title}}</div>
                                    </div>
                                    <div class="fs-6 d-flex align-items-center pt-3" style="color: #10B981"> 
                                        <span class="material-symbols-outlined me-1 ">
                                            event
                                        </span>
                                        {{ task.dateCreated}}
                                    </div>
                                    <div class="fs-6 d-flex align-items-center pt-3" style="color: #10B981">
                                        <span class="material-symbols-outlined me-1 ">
                                            event
                                        </span>
                                        {{ task.dateFinished}}
                                    </div>
                                    <hr>
                                    <button type="button" class="view-btn" style="color: #10B981" @click="openModal(task)">
                                        View
                                    </button>
                                    |
                                    <button type="button" class="view-btn" style="color: #10B981" @click="updateStatus(task)">
                                        Update Status
                                    </button>
                                    <div>
                                        <div class="modal fade" :id="'taskModal-' + task.userTasksId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                          <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header d-flex flex-column align-items-start ">
                                                    <label for="">Task Name: </label>
                                                    <h5 class="modal-title w-100" id="exampleModalLabel">
                                                        <input type="text" v-model="task.task.title" class="p-2 w-100">
                                                    </h5>
                                                </div>
                                              <div class="modal-body">
                                                <div class="w-100">
                                                    <textarea v-model="task.task.description" class="w-100 p-2" placeholder="description" style="resize: none" rows="4"></textarea>
                                                </div>
                                                <div>
                                                    <label>Status: </label> 
                                                    <div style="background: #10B981;" class="w-25 text-white text-center p-2 rounded-1 mt-1">{{task.status}}</div>
                                                </div>
                                              </div>
                                              <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" @click="closeModal">Close</button>
                                                <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
                                                <button v-if="!task.task.isMandatory" type="button" class="btn btn-danger" @click="deleteTask(task)">Delete</button>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useTaskStore } from '@/stores/TaskStore';
import bootstrapBundle from 'bootstrap/dist/js/bootstrap.bundle';
import { ref } from 'vue';
    export default {
        setup() {
            const taskStore = useTaskStore()
            const task = ref({
                taskName: '',
                taskDescription: ''
            })
            const selectedTask = ref()
            
            taskStore.resetStore()
            taskStore.getAllTask()
 
            const openModal = (task) => {
                selectedTask.value = task
                const modalId = `taskModal-${task.userTasksId}`
                const modal = new bootstrapBundle.Modal(document.getElementById(modalId))
                modal.show()
            }
        
            const closeModal = () => {
                const modalId = `taskModal-${selectedTask.value.userTasksId}`
                const modal = bootstrapBundle.Modal.getInstance(document.getElementById(modalId))
                if (modal) {
                    modal.hide()
                }
            }

            const saveChanges = () => {
                taskStore.updateTask(selectedTask.value, selectedTask.value.userTasksId)
                closeModal()
            }

            const updateStatus = (task) => {
                if(task.status==="TODO") {
                    task.status="IN PROGRESS"
                } else if(task.status==="IN PROGRESS") {
                    task.status="DONE"
                } else if(task.status==="DONE") {
                    task.status="TODO"
                }
                taskStore.updateTask(task, task.userTasksId)
            }

            const deleteTask = (task) => {
                taskStore.deleteTask(task.userTasksId)
                closeModal()
            }

            const createTask = () => {
                taskStore.createTask(task.value)
            }

            return {
                taskStore,
                task,
                openModal,
                closeModal,
                saveChanges,
                updateStatus,
                deleteTask,
                createTask
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
.mandatory-task-list {
    border-left: 5px solid #FFBF44;
}
.task-done {
    border-left: 5px solid #10b981;
}
.task-in-progress {
    border-left: 5px solid #3B82F6;
}
.task-todo {
    border-left: 5px solid #8b5cf6;
}
.view-btn {
    border: none;
    color: blue;
    background: none;
}
.error-message {
    color: red;
}
</style>