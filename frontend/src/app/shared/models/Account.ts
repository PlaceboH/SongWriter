export enum SubscriptionType {
    Open,
    Close,
}

export enum AccountGoalTags {
    Lyrics,
    Notes,
    Tabs,
    Melody,
    Accompaniment,
}

export interface Account {
    id: number;
    userId: number;
    subscriptionType: SubscriptionType;
    accountGoalTags: AccountGoalTags[];
}
