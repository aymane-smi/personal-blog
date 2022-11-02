import { connect } from "http2";
import { NextApiRequest, NextApiResponse } from "next";
import { editUser } from "../../../controllers/user";
import { msgError } from "../../../utils/types";


export default async function handler(req: NextApiRequest, res: NextApiResponse<msgError>){
    await connect(process.env.MONGODB_URL);
    if(req.method === "POST")
        editUser(req, res);
    else
        res.status(500).json({
            message: `${req.method} not supported for this endpoint`,
        });
}; 