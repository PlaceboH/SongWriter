import { Pipe, PipeTransform } from '@angular/core';
import { User } from '../models/User';


@Pipe({
    standalone: true,
    name: 'searchUser'
})
export class SearchUserPipe implements PipeTransform {
     transform(users: User[], userData: string): User[] {
        if (userData) {
          return users.filter(user => {
            return user.username.includes(userData) || user.firstname.includes(userData) || user.lastname.includes(userData);  
          })
        }
        return users;
      }
 }