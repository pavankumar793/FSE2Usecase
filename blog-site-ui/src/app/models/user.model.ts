export class UserRegister {
  constructor(
    public name: string = "",
    public address: string = "",
    public email: string = "",
    public username: string = "",
    public password: string = "",
    public gender: string = "",
    public dateOfBirth: Date = new Date()
  ) {}
}

export class UserLogin {
  constructor(
    public usernameOrEmail: string = "",
    public password: string = ""
  ) {}
}