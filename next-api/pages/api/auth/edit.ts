import { connect } from "http2";
import { NextApiRequest, NextApiResponse } from "next";
import { editUser } from "../../../controllers/user";
import { msgError, userResponse } from "../../../utils/types";


export default async function handler(req: NextApiRequest, res: NextApiResponse<userResponse | msgError>){
    if(req.method === "PUT"){
        //console.log(req.body, process.env.MONGODB_URL);
        await connect(process.env.MONGODB_URL);
        await editUser(req, res);
    }
    else
        res.status(500).json({
            message: `${req.method} not supported for this endpoint`,
        });
}; 