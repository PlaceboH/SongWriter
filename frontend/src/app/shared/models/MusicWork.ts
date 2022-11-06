import { Mark } from "./Mark";

export interface MusicWork {
    id?: number;
    title: string;
    description?: string;
    lyrics?: string;
    chords?: string;
    notes?: File;
    image?: File;
    marks: Mark[];
    username: string;
}