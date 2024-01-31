
export interface GlobalApiHandler<T> {
    token(arg0: string, token: any): unknown;
    status: string;
    httpStatus: string;
    timestamp: Date;
    message: string;
    data: T
  }