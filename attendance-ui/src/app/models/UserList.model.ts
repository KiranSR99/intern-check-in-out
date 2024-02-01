import { RoleEnum } from "../core/enums/role.enum";

export class UserList {
    id: number | undefined;
    fullName: string | undefined;
    email: string | undefined;
    password: string | undefined;
    role: RoleEnum | undefined;

}