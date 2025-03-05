export class HttpResponseModel<T> {
  status: string | undefined;
  message: string | undefined;
  results: T | undefined;
  result: T | undefined;
}
