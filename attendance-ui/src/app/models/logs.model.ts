import { UserList } from "./UserList.model";

export class LogsDetails {
    Tasks: Array<Task> | undefined;
}

export interface Task{
    id: number ;
    task: string ;
    status: string ;
    timeTaken: string ;
    problem: string ;
    userId: UserList;
}