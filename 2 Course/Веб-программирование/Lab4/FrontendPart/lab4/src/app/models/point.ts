export class Point {
  constructor(
    public id: number,
    public x: number,
    public y: number,
    public r: number,
    public result: boolean,
    public time: string,
    public user: {username: string}){}
}
