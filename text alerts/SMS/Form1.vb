Imports System
Imports System.Reflection
Imports System.Web
Imports System.Object
Imports System.Net
Imports System.IO

Public Class Form1

    Private Sub btn_clear_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button1.Click, Btn_clear.Click

        RichTextBox2.Text = ""

    End Sub
    Public Sub send_sms(ByVal msg As String)
        ' Dim wsh As Object = CreateObject("WScript.Shell")
        'wsh.run("DisableIEProxy.vbs")

        Dim s As String
        s = RichTextBox1.Text
        Dim s1() As String
        s1 = s.Split(New Char() {","c})
        Call sms_way2sms("9035265051", "4233", "way2SMS", s1, msg)
    End Sub

    Public Sub sms_way2sms(ByVal uid As String, ByVal password As String, ByVal Message As String, ByVal no() As String, ByVal msg As String)
        Try
            For i = 0 To no.Length - 1

                Dim uri As New Uri("http://ubaid.tk/sms/sms.aspx?uid=" + uid + "&pwd=" + password + "&msg=" + msg + "&phone=" + no(i) + "&provider=" + Message)
                Dim request As HttpWebRequest = HttpWebRequest.Create(uri)
                request.Method = WebRequestMethods.Http.Get
                Dim response As HttpWebResponse = request.GetResponse()
                Dim reader As New StreamReader(response.GetResponseStream())
                Dim tmp As String = reader.ReadToEnd()

                If (tmp = 1) Then
                    '  WifiRich.AppendText("Delivered to " + no(i) + " from " + Message + vbCrLf)

                Else
                    ' WifiRich.AppendText("Not Delivered to " + no(i) + " from " + Message + vbCrLf)
                End If
                ' RichTextBox4.Text = tmp.ToString()
                response.Close()

            Next
        Catch ex As Exception
            MsgBox(ex.Message)
        End Try



    End Sub


    Private Sub Button1_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button1.Click
        Dim txt As String
        txt = RichTextBox2.Text
        send_sms(txt)

    End Sub

End Class
