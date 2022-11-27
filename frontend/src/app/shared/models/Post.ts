import { Comment } from "./Comments";

export interface Post {
    id?: number;
    title: string;
    caption: string;
    image?: File;
    likes?: number;
    userLiked?: string[];
    comments?: Comment[];
    username?: string;
}