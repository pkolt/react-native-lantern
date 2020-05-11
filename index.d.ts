declare function ready(): Promise<void>;
declare function turn(turnState: boolean): Promise<void>;
declare function turnOn(): Promise<void>;
declare function turnOff(): Promise<void>;

export interface Event {
    value: boolean;
}

export interface Callback {
    (event: Event): void
}

export interface Unsubscribe {
    (): void
}

declare function subscribe(eventName: 'onTurn', callback: Callback): Unsubscribe;
