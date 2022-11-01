// Next.js API route support: https://nextjs.org/docs/api-routes/introduction
import type { NextApiRequest, NextApiResponse } from 'next'
import { User } from "./../../models/user";
import { connect } from "mongoose";

type Data = {
  name: string
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse<Data>
) {
  let test = await connect("mongodb://localhost:27017/personal-blog");
  console.log(test);
  res.status(200).json({ name: 'John Doe' })
}
