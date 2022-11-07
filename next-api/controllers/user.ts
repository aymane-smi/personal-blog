import { loginResponse, msgError, user, userResponse } from "../utils/types";
import { User } from "../models/user";
import { NextApiRequest, NextApiResponse } from "next";
import * as bcrypt  from "bcryptjs";
import { sign } from "jsonwebtoken";
import { connection } from "mongoose";

export const signup = async (req: NextApiRequest, res: NextApiResponse<userResponse | msgError>)=>{
    try{
        req.body.password = await bcrypt.hash(req.body.password, 12);
        const result = await User.create(req.body);
        console.log("user created!");
        connection.close();
        return res.status(200).json({
            user: result,
            message: "user created",
        });
    }catch(e){
        return res.status(500).json({
            message: "something went wrong!",
        })
    }

};

export const editUser = async(req: NextApiRequest, res: NextApiResponse<userResponse | msgError>)=>{
    const obj = {...req.body};
    delete obj.id; 
    try{
        if(obj.password)
            obj.password = await bcrypt.hash(obj.password, 12);
        const result = await User.findByIdAndUpdate(req.body.id, obj);
        connection.close();
        return res.status(200).json({
            message: "user updated!",
            user: result,
        });
    }catch(e){
        console.log(e);
        return res.status(500).json({
            message: "something went wrong!",
        });
    }
};


export const login = async(req: NextApiRequest, res: NextApiResponse<loginResponse | msgError>)=>{
    try{
        if(req.body.password === '' || !req.body.password)
            return res.status(404).json({
                message: "invalide user/password!",
            });
        const { username } = req.body;
        const user = await User.findOne({username,});
        connection.close();
        if(!user)
            return res.status(404).json({
                message: "invalid user/password"
            });
        const compare = bcrypt.compare(req.body.password, user.password);
        if(!user && !compare)
            return res.status(400).json({
                message: "invalid user/password!",
            });
        let token = sign({
            username: user.username,
            password: user.password,
            Fullname: user?.Fullname,
        }, process.env.SECRET_KEY as string, {expiresIn: "24h"});
        return res.status(200).json({
            message: "user logged in!",
            user: {
                username: user.username,
                password: user.password,
                Fullname: user.Fullname
            },
            token,
        });
        
    }catch(e){
        console.log(e);
        return res.status(500).json({
            message: "something went wrong!",
        })
    }
};