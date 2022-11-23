
export interface User {
    id: number;
    email: string;
    username: string;
    firstname: string;
    lastname: string;
    bio: string;
    userFollowers: string[];
    subscribedUsers: string[]; 
}