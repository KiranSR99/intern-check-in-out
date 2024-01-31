export interface LogsDetails {
    id: number ;
    task: string ;
    status: string ;
    timeTaken: string ;
    problem: string ;
    multiLogDetails: MultiLogDetails[];
}

export interface MultiLogDetails{
    id: number ;
    task: string ;
    status: string ;
    timeTaken: string ;
    problem: string ;
}