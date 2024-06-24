export class Blog {
    constructor(
      public blogId: string = "",
      public blogName: string = "",
      public blogCategory: string = "",
      public blogArticle: string = "",
      public blogAuthor: string = "",
      public blogCreatedOn: string = "",
    ) {}
  }

export class BlogRequest {
    constructor(
      public blogId: string = "",
      public blogName: string = "",
      public blogCategory: string = "",
      public blogArticle: string = "",
      public blogAuthor: string = "",
      public userId: string = ""
    ) {}
  }