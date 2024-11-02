<template>
    <div id="services" class="container p-0 my-5">
        <div class="row m-0">
            <div class="col-lg col-sm-12">
                <div>
                    <h3>Important Services in Trend Micro</h3>
                </div>
                <hr>
                <div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                            <th scope="col">Service Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Link</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="service in services" :key="service.service_id">
                            <td>{{service.service_name}}</td>
                            <td>{{service.service_description}}</td>
                            <td>
                                <a :href="service.link" target="_blank">
                                    Visit
                                </a>
                            </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-lg col-sm-12">
                <div>
                    <h3>Important Links</h3>
                </div>
                <hr>
                <div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Address Name</th>
                                <th scope="col">Link</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="link in links" :key="link.link_id">
                                <td>{{link.link_name}}</td>
                                <td><a :href="link.link" target="_blank">Visit</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import importantService from '../assets/data/services.json'
import ImportantLinks from '../assets/data/links.json'
    export default {
        data() {
            return {
                services: importantService,
                links: ImportantLinks
            }
        }
    }
</script>

<style scoped>
a {
    text-decoration: none;
}
</style>