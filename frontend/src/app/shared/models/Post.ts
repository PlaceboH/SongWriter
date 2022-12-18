import { Comment } from './Comments';
import { User } from './User';

export interface Post {
    id?: number;
    title: string;
    caption: string;
    image?: File;
    likes?: number;
    usersLiked?: string[];
    comments?: Comment[];
    username?: string;
    user?: User;
}
