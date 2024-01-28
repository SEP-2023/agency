export class AgencyInfoDto{
  constructor(
    public agencyId: string = '',
    public transactionId: string = '',
    public amount: string = '',
    public accessToken: string = '',
  ) {}
}
