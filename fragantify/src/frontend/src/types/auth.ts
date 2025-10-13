export interface User {
  username: string;
  roles: string[];
}

export interface AuthResponse {
  token: string;
  user: User;
}
