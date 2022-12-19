import { Mark } from './Mark';
import { User } from './User';

export interface MusicWork {
    id?: number;
    title: string;
    description?: string;
    caption?: string;
    lyrics?: string;
    chords?: string;
    notes?: File;
    image?: File;
    marks?: Mark[];
    username?: string;
    user?: User;
}
