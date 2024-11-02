import router from '@/router'
import { defineStore } from 'pinia'

export const useTaskStore = defineStore('taskStore', {
    state: () => ({
        tasks: [],
        error: '',
        success: false,
    }),
    actions: {
        async getAllTask() {            
            try {
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/tasks/${localStorage.getItem('userId')}`, {
                  method: 'GET',
                  headers: { 
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}` 
                  }
            })
            const data = await response.json()
            this.tasks.push(data)
            } catch (error) {
                console.error('Error fetching task:', error)
            }
        },

        async createTask(task) {
            try {
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/tasks/create/${localStorage.getItem('userId')}`, {
                    method: 'POST',
                    headers: { 
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('authToken')}` 
                    },
                    body: JSON.stringify({
                        title: task.taskName,
                        description: task.taskDescription,
                    })
                })
                if(!response.ok) {
                    const data = await response.json()
                    this.error = data.message
                } else {
                    this.error = "Task create successfully."
                    this.success = true
                    setTimeout(() => {
                        window.location.reload()
                    }, 1000)
                    this.resetFrom() 
                }
            } catch (error) {
                console.error('Error fetching task:', error)
            }
        },

        async updateTask(task, userTaskId) {
            try {
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/tasks/update/${localStorage.getItem('userId')}?userTaskId=${userTaskId}`, {
                  method: 'PUT',
                  headers: { 
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}` 
                  },
                  body: JSON.stringify(task)
                })
  
                if(response.ok) {
                    alert("tasks updated successfully!")
                    window.location.reload()
                    this.getAllTask
                }
            } catch (error) {
                console.error('Error fetching task:', error)
            }
        },

        async deleteTask(userTaskId) {
            try {
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/tasks/delete/${userTaskId}`, {
                  method: 'DELETE',
                  headers: { 
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}` 
                  }
                })
                if(response.ok) {
                    alert("task deleted successfully!")
                    window.location.reload()
                    this.getAllTask
                }
            } catch (error) {
                console.error('Error fetching task:', error)
            }
        },

        logout() {
            localStorage.clear()
            this.$reset()
            router.push('/sign-in')
        },

        resetStore() {
            this.tasks = [],
            this.error = ''
        }
    },
    getters: {
        allTaskCount(state) {
            return state.tasks.flat().length
        },
        mandatoryTask(state) {
            return state.tasks.flat() || [].filter(taskEntry => taskEntry.task.isMandatory)
        },
        mandatoryTaskCount(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.task.isMandatory === true).length
        },
        tasksTodo(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.status === "TODO")
        },
        tasksTodoCount(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.status === "TODO").length
        },
        tasksInProgress(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.status === "IN PROGRESS")
        },
        tasksInProgressCount(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.status === "IN PROGRESS").length
        },
        tasksDone(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.status === "DONE")
        },
        tasksDoneCount(state) {
            return (state.tasks.flat() || []).filter(taskEntry => taskEntry.status === "DONE").length
        },
        doneMandatoryTaskCount(state) {
            return (state.tasks.flat() || []).filter(taskEntry => 
              taskEntry.status === "DONE" && taskEntry.task.isMandatory
            ).length
        },
    }
})