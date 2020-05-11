export class Event {
  public eventCode: string;
  public startsAt: string;  // will be LocalDateTime in backend
  public endsAt: string; // will be LocalDateTime in backend
  public name: string;
  public description: string;
  public startPrice: number;
  public photo: number;
  public List<Show> shows;
  public List<String> artists;
  public type: string;
  public category: string;
  public List<Integer> prices;
  public totalTicketsSold: number;

}
