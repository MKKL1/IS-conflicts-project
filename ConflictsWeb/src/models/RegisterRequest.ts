export class RegistrationRequest{
    public username: string;
    public password: string;
    public passwordConfirm: string;

    constructor(username: string, password: string, passwordConfirm: string) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}